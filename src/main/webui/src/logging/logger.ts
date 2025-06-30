interface LogFn {
  (message?: any, ...optionalParams: any[]): void;
}

interface Logger {
  debug: LogFn;
  info: LogFn;
  warn: LogFn;
  error: LogFn;
}

export type LogLevel = 'debug' | 'info' | 'warn' | 'error';

const logger: Logger = {
  debug: (message?: any, ...optionalParams: any[]) => {
    console.info(message, ...optionalParams);
  },
  info: (message?: any, ...optionalParams: any[]) => {
    console.info(message, ...optionalParams);
  },
  warn: (message?: any, ...optionalParams: any[]) => {
    console.warn(message, ...optionalParams);
  },
  error: (message?: any, ...optionalParams: any[]) => {
    console.error(message, ...optionalParams);
  },
};

export default logger;
