import { BiCheckCircle, BiError, BiErrorCircle, BiInfoCircle } from "react-icons/bi";

interface AlertProps {
  children: React.ReactNode;
  type?: 'error' | 'warning' | 'info' | 'success';
}

const NoStyledAlert = ({ children }: AlertProps) => {
  return (
    <div role="alert" className="alert">
      <BiInfoCircle />
      {children}
    </div>
  );
};

const AlertInfo = ({ children }: AlertProps) => {
  return (
    <div role="alert" className="alert alert-info">
      <BiInfoCircle />
      {children}
    </div>
  );
};

const AlertWarning = ({ children }: AlertProps) => {
  return (
    <div role="alert" className="alert alert-warning">
      <BiError />
      {children}
    </div>
  );
};

const AlertSuccess = ({ children }: AlertProps) => {
  return (
    <div role="alert" className="alert alert-success">
      <BiCheckCircle />
      {children}
    </div>
  );
};

const AlertError = ({ children }: AlertProps) => {
  return (
    <div role="alert" className="alert alert-error">
      <BiErrorCircle />
      {children}
    </div>
  );
};

const Alert = ({ children, type }: AlertProps) => {
  switch (type) {
    case 'error':
      return <AlertError>{children}</AlertError>;
    case 'warning':
      return <AlertWarning>{children}</AlertWarning>;
    case 'info':
      return <AlertInfo>{children}</AlertInfo>;
    case 'success':
      return <AlertSuccess>{children}</AlertSuccess>;
    default:
      return <NoStyledAlert>{children}</NoStyledAlert>;
  }
};

export default Alert;
