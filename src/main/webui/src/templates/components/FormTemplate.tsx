import { v4 as uuidv4 } from "uuid";
import { clsx } from 'clsx';
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import Button from "../../atoms/Button";
import type { Template } from "../models/Template";

const templateSchema = z.object({
  id: z.string().uuid(),
  name: z.string().min(1),
  description: z.string().min(1),
});

interface TemplateForm extends z.infer<typeof templateSchema> { }

interface FormTemplateProps {
  onSubmit: (template: Template) => Promise<void> | void;
  onCancel: () => Promise<void> | void;
}

const FormTemplate = ({ onSubmit, onCancel }: FormTemplateProps) => {
  const { reset, register, handleSubmit, formState: { errors } } = useForm<TemplateForm>({
    resolver: zodResolver(templateSchema),
    defaultValues: {
      id: uuidv4(),
      name: '',
      description: '',
    },
  });

  const handleOnCancel = async () => {
    await onCancel();
    reset();
  }

  const handleOnSubmit = async (template: TemplateForm) => {
    await onSubmit(template as Template); // parsing from zod Template to Domain Template
    reset();
  }

  return (
    <form onSubmit={handleSubmit(handleOnSubmit)}>
      <fieldset className="fieldset w-full">
        <label className="label" htmlFor="title">Title</label>
        <input id="title"
          type="text"
          className={clsx(
            'input w-full',
            errors.name && "input-error"
          )}
          placeholder="Enter template title"
          {...register('name')} />
        <p className="text-error">
          {errors.name?.message}
        </p>

        <label className="label" htmlFor="description">Description</label>
        <textarea id="description"
          rows={5}
          className={clsx(
            'textarea w-full',
            errors.description && "input-error"
          )}
          placeholder="Enter template description"
          {...register('description')} />
        <p className="text-error">
          {errors.description?.message}
        </p>
      </fieldset>

      <div>
        <Button type="button" color="ghost" onClick={handleOnCancel}>Cancel</Button>
        <Button type="submit" color="primary">Create Template</Button>
      </div>

    </form>

  )
}

export default FormTemplate;
