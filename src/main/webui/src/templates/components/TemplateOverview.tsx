import Button from "../../atoms/Button";
import type { Template } from "../models/Template";
import { BiLock } from "react-icons/bi";

interface TemplateOverviewProps {
  template: Template;
}

const TemplateOverview = ({ template }: TemplateOverviewProps) => {
  const { name, description, itemsCount } = template;
  return (
    <>
      <div className="card bg-base-200 w-auto shadow-sm">
        <div className="card-body">
          <div className="flex justify-end items-center">
            {/* <span className="badge">Productivity</span> */}
            <BiLock className="fill-secondary" />
          </div>
          <h2 className="card-title">{name}</h2>
          <p>{description}</p>

          <div className="flex justify-between">
            <span>{itemsCount} Items</span>
            {/* <span>234 uses</span> */}
          </div>

          <div className="card-actions justify-end items-center">
            {/* <span>by Sarah Drasner</span> */}
            <Button color="primary">Use Template</Button>
          </div>
        </div>
      </div>
    </>
  );
};

export default TemplateOverview;
