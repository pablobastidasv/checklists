import AppBar from "../../molecules/AppBar";
import Menu from "../components/Menu";
import Templates from "../components/Templates";
import Welcome from "../components/Welcome";

const HomePage = () => {
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
