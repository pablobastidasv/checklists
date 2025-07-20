import { useEffect, useState } from "react";
import getTemplates from "../api/GetTemplates";
import type { Event, TemplateCreated } from "../models/Events";
import type { Template } from "../models/Template";
import useSSE from "./useSSE";

interface ListenOptions {
  onCreated: (t: Template) => void;
}

const listen = ({ onCreated }: ListenOptions) => {
  return useSSE("/api/templates/events", (event: Event) => {
    if (event.type === 'TEMPLATE_CREATED') {
      const templateCreatedContent = event.content as TemplateCreated;

      const template = {
        id: templateCreatedContent.id,
        name: templateCreatedContent.name,
        description: templateCreatedContent.description,
        itemsCount: 0,
      }

      onCreated(template);
    }
  });
}

const useListTemplates = () => {
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

  const sseData = listen({
    onCreated: (template: Template) => setTemplates(old => [template, ...old]),
  });

  return {
    setTemplates,
    templates,
    error,
    loading,
    sseData
  }

}

export default useListTemplates;
