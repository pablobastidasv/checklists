import Alert from "../../atoms/Alert";
import Spinner from "../../atoms/Spinner";
import useListTemplates from "../hooks/useListTemplates";
import SseInfoPanel from "./SseInfoPanel";
import TemplateList from "./TemplateList";

const Templates = () => {
  const { templates, error, loading, sseData } = useListTemplates();

  if (loading) {
    return <Spinner />;
  }

  if (error) {
    return <Alert type="error">{error}</Alert>;
  }

  return (
    <>
      {templates.length === 0
        ? <Alert>No templates found</Alert>
        : <>
          <SseInfoPanel data={sseData} />
          <TemplateList templates={templates} />
        </>
      }
    </>
  );
};

export default Templates;
