import type { SSEData } from "../hooks/useSSE";

interface SseInfoPanelProps {
  data: SSEData;
}
const SseInfoPanel = ({ data }: SseInfoPanelProps) => {
  return (
    <>
      Sse status = {data.connectionStatus}
      {data.error && <p>Error: {data.error}</p>}
    </>
  )
}

export default SseInfoPanel;
