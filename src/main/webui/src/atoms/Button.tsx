import type React from "react";

interface ButtonProps extends React.ComponentPropsWithoutRef<'button'> {
  color?: 'neutral' | 'primary' | 'secondary' | 'accent' | 'info' | 'success' | 'warning' | 'error' | 'ghost' | 'link'
}


const Button: React.FC<ButtonProps> = ({ children, color, ...props }) => {
  return (
    <button className={`btn ${color ? `btn-${color}` : 'btn-neutral'}`} {...props}>
      {children}
    </button>
  );
};

export default Button;
