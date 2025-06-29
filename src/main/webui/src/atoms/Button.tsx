import type React from "react";

interface ButtonProps extends React.ComponentPropsWithoutRef<'button'> {
  color?: 'primary' | 'secondary' | 'accent' | 'ghost' | 'link' | 'info' | 'success' | 'warning' | 'error'
}


const Button: React.FC<ButtonProps> = ({ children, color, ...props }) => {
  return (
    <button className={`btn ${color ? `btn-${color}` : 'btn-neutral'}`} {...props}>
          {children}
    </button>
  );
};

export default Button;
