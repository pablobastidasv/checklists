import type { Template } from "../models/Template";
import TemplateOverview from "./TemplateOverview";

interface TemplateListProps {
  templates: Template[];
}

const TemplateList = ({ templates }: TemplateListProps) => {
  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 my-4">
      {templates.map((template) => (
        <TemplateOverview template={template} key={template.id} />
      ))}
    </div>
  )
}

export default TemplateList;
