import Hero from "../../molecules/Hero";

const Welcome = () => {
  return (
    <Hero>
      <div className="max-w-md my-10">
        <h1 className="text-5xl font-bold">Template Gallery ✨</h1>
        <p className="py-6">
          Discover amazing templates created by our community or buold your own to share with the world.
        </p>
      </div>
    </Hero>
  );
};

export default Welcome;
