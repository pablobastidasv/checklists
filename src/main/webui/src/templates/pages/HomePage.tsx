import AppBar from "../../molecules/AppBar";
import Templates from "../components/Templates";
import Welcome from "../components/Welcome";
import Button from "../../atoms/Button";
import CreateTemplateFormModal from "../components/CreateTemplateForm";

const HomePage = () => {

  const Menu = () => {
    return (
      <>
        <div className="flex gap-2 my-4">
          <CreateTemplateFormModal />
          <Button>My Templates</Button>
        </div>
      </>
    );
  };


  return (
    <>
      <AppBar />
      <Welcome />
      <main className="container mx-auto">
        <Menu />
        <Templates />
      </main>
    </>
  );
};


export default HomePage;
