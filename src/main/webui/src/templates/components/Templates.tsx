import { useEffect, useState } from "react";
import getTemplates from "../api/GetTemplates";
import TemplateOverview from "./TemplateOverview";
import type { Template } from "../models/Template";

const Templates = () => {
  const [templates, setTemplates] = useState<Template[]>([]);

  useEffect(() => {
    if (templates.length !== 0) {
      return;
    }

    const fetchTemplates = async () => {
      const templates = await getTemplates();
      setTemplates(templates);
    };

    fetchTemplates();
  }, []);

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
