import { useEffect, useRef } from "react";
import { BiX } from "react-icons/bi";

interface ModalProps {
  title?: string;
  isOpen: boolean;
  onClose: () => void;
  children?: React.ReactNode;
  footer?: React.ReactNode;
  onOpen?: () => void;
}

const Modal = ({ isOpen, onClose, children, title, footer, onOpen }: ModalProps) => {
  const ref = useRef<HTMLDialogElement>(null);
  const prevIsOpen = useRef<boolean>(false);

  useEffect(() => {
    const dialog = ref.current;
    if (!dialog) return;

    if (isOpen && !prevIsOpen.current) {
      if (onOpen) {
        onOpen();
      }

      dialog.showModal();
    } else if (!isOpen && prevIsOpen.current) {
      dialog.close();
    }

    prevIsOpen.current = isOpen;
  }, [isOpen, onOpen]);

  const handleBackdropClick = (e: React.MouseEvent<HTMLDialogElement>) => {
    if (e.target === ref.current) {
      e.preventDefault();
      onClose();
    }
  };

  const handleDialogClose = () => {
    onClose();
  };

  return (
    <>
      <dialog className="modal"
        onClick={handleBackdropClick}
        onClose={handleDialogClose}
        ref={ref}>
        <div className="modal-box">
          <div className="flex justify-between">
            <h3 className="font-bold text-lg">{title ?? ""}</h3>
            <a className="cursor-pointer" onClick={onClose}><BiX /></a>
          </div>
          {children}
          {footer && <div className="modal-action">{footer}</div>}
        </div>
      </dialog>
    </>
  );
};

export default Modal;
