import { useState } from "react";

import Button from "../../atoms/Button";
import Modal from "../../molecules/Modal";
import { BiPlus } from "react-icons/bi";
import postTemplate from "../api/PostTemplate";
import FormTemplate from "./FormTemplate";
import type { Template } from "../models/Template";

const CreateTemplateFormModal = () => {
  const [open, setOpen] = useState(false);

  const _closeModal = () => {
    setOpen(false);
  }

  const handleOnCancel = () => {
    _closeModal();
  }

  const onSubmit = async (template: Template) => {
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
        onClose={handleOnCancel}
        title="Create Template">
        <FormTemplate onSubmit={onSubmit}
          onCancel={handleOnCancel}
        />
      </Modal>
    </>
  );
}

export default CreateTemplateFormModal;
