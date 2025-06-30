import { useEffect, useState } from "react";
import getTemplates from "../api/GetTemplates";
import TemplateOverview from "./TemplateOverview";
import type { Template } from "../models/Template";
import Alert from "../../atoms/Alert";
import Spinner from "../../atoms/Spinner";

const Templates = () => {
  const [templates, setTemplates] = useState<Template[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (templates.length !== 0) {
      return;
    }
    setLoading(true);

    const fetchTemplates = async () => {
      const { data, error } = await getTemplates();
      setTemplates(data);
      setError(error);
      setLoading(false);
    };

    fetchTemplates();
  }, []);

  if (loading) {
    return <Spinner />;
  }

  if (error) {
    return <Alert type="error">{error}</Alert>;
  }

  if (!templates.length) {
    return <Alert>No templates found</Alert>;
  }

  return (
    <>
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 my-4">
        {templates.map((template) => (
          <TemplateOverview template={template} key={template.id} />
        ))}
      </div>
    </>
  );
};

export default Templates;
