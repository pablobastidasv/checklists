import { useState } from "react";

import { BiPlus } from "react-icons/bi";
import { v4 as uuidv4 } from "uuid";
import Button from "../../atoms/Button";
import Modal from "../../molecules/Modal";
import postTemplate from "../api/PostTemplate";
import { FormTemplate, type TemplateFormFields } from "./FormTemplate";

const CreateTemplateFormModal = () => {
  const [open, setOpen] = useState(false);
  const [id, setId] = useState(uuidv4());

  const onModalOpen = () => {
    setId(uuidv4());
  }

  const _closeModal = () => {
    setOpen(false);
  }

  const handleOnCancel = () => {
    _closeModal();
  }

  const onSubmit = async (template: TemplateFormFields) => {
    await postTemplate({ ...template, id });
    _closeModal();
  }

  return (
    <>
      <Button onClick={() => setOpen(true)} color="primary">
        <BiPlus />
        Create Template
      </Button>
      <Modal isOpen={open}
        onOpen={onModalOpen}
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
