import { useState } from "react";

import { v4 as uuidv4 } from "uuid";
import Button from "../../atoms/Button";
import Modal from "./Modal";
import { BiPlus } from "react-icons/bi";
import { clsx } from 'clsx';
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import postTemplate from "../api/PostTemplate";

const templateSchema = z.object({
  id: z.string().uuid(),
  name: z.string().min(1),
  description: z.string().min(1),
});

interface TemplateForm extends z.infer<typeof templateSchema> { }

const CreateTemplateFormModal = () => {
  const [open, setOpen] = useState(false);
  const { reset, register, handleSubmit, formState: { errors } } = useForm<TemplateForm>({
    resolver: zodResolver(templateSchema),
    defaultValues: {
      id: uuidv4(),
      name: '',
      description: '',
    },
  });

  const handleOnCancel = () => {
    _closeModal();
  }

  const handleOnOpen = () => {
    reset();
  }

  const _closeModal = () => {
    setOpen(false);
  }

  const onSubmit = async (template: TemplateForm) => {
    await postTemplate(template);
    _closeModal();
  }

  return (
    <>
      <Button onClick={() => setOpen(true)} color="primary">
        <BiPlus />
        Create Template
      </Button>
      <Modal isOpen={open}
        onOpen={handleOnOpen}
        onClose={handleOnCancel}
        title="Create Template">
        <form onSubmit={handleSubmit(onSubmit)}>
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
      </Modal>
    </>
  );
}

export default CreateTemplateFormModal;
