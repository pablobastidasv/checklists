import Button from "../../atoms/Button";
import { BiPlus } from "react-icons/bi";

const Menu = () => {
  return (
    <>
      <div className="flex gap-2 my-4">
        <Button color="primary">
          <BiPlus />
          Create Template
        </Button>
        <Button>My Templates</Button>
      </div>
    </>
  );
};

export default Menu;
