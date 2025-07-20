import { useEffect, useRef, useState } from "react";
import { logger } from "../../logging";
import type { Event } from "../models/Events";

type SSEConnectionStatus = 'disconnected' | 'connected' | 'connecting' | 'error';

interface SSEData {
  connectionStatus: SSEConnectionStatus;
  error: string | null;
}

const useSSE = (url: string, listener: (t: Event) => void): SSEData => {
  const [connectionStatus, setConnectionStatus] = useState<SSEConnectionStatus>('disconnected');
  const abortControllerRef = useRef<AbortController | null>(null);
  const readerRef = useRef<ReadableStreamDefaultReader | null>(null);
  const [error, setError] = useState<string | null>(null);

  const connectToSSE = async () => {
    if (connectionStatus === 'connecting' || connectionStatus === 'connected') {
      logger.debug('Already connected');
      return;
    }

    logger.info('Connecting to SSE...');

    setConnectionStatus('connecting');
    setError(null);

    try {
      // Create AbortController for clean disconnection
      abortControllerRef.current = new AbortController();

      const response = await fetch(url, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
          'Accept': 'text/event-stream',
          'Cache-Control': 'no-cache'
        },
        signal: abortControllerRef.current.signal // Add abort signal
      });

      if (!response.ok) {
        throw new Error(`HTTP ${response.status}: ${response.statusText}`);
      }

      const reader = response.body?.getReader();
      if (!reader) {
        throw new Error('No reader available');
      }

      readerRef.current = reader;
      setConnectionStatus('connected');

      const decoder = new TextDecoder();
      let buffer = '';

      try {
        while (true) {
          logger.debug('Waiting for data...');
          const { done, value } = await reader.read();
          if (done) break;

          buffer += decoder.decode(value, { stream: true });
          const lines = buffer.split('\n');
          buffer = lines.pop() || '';

          logger.debug('useSSE: Received data:', lines);
          for (const line of lines) {
            logger.debug('useSSE: Received line:', line);
            if (line.startsWith('data:')) {
              try {
                const data = line.substring(5);
                const jsonData = JSON.parse(data);
                logger.debug('useSSE: Received SSE data:', jsonData);
                listener(jsonData);
              } catch (parseError) {
                logger.error('useSSE: Error parsing SSE data:', parseError);
              }
            }
          }
        }
      } catch (readError) {
        if (readError instanceof DOMException && readError.name === 'AbortError') {
          logger.debug('SSE connection aborted cleanly');
          setConnectionStatus('disconnected');
        } else {
          throw readError;
        }
      } finally {
        // Clean up reader
        logger.debug('Releasing reader');
        if (readerRef.current) {
          readerRef.current.releaseLock();
          readerRef.current = null;
        }
      }
    } catch (err) {
      // Check for various abort-related errors
      const isAbortError =
        (err instanceof DOMException && err.name === 'AbortError') ||
        (err instanceof Error && err.name === 'AbortError') ||
        (err instanceof Error && err.message.includes('aborted')) ||
        (abortControllerRef.current?.signal.aborted);

      if (isAbortError) {
        logger.debug('SSE connection aborted cleanly');
        setConnectionStatus('disconnected');
      } else {
        logger.error('Error with fetch SSE:', err);
        setConnectionStatus('error');
        setError(err instanceof Error ? err.message : 'Unknown error');
      }
    }
  };

  const disconnect = () => {
    if (abortControllerRef.current) {
      abortControllerRef.current.abort();
      abortControllerRef.current = null;
    }

    // Clean up reader
    if (readerRef.current) {
      readerRef.current.releaseLock();
      readerRef.current = null;
    }

    setConnectionStatus('disconnected');
    setError(null);
  };

  useEffect(() => {
    connectToSSE();

    return () => {
      logger.debug('Disconnecting from SSE...');
      disconnect();
    };
  }, []);

  return {
    connectionStatus,
    error,
  };
}

export default useSSE;
export type { SSEData };
