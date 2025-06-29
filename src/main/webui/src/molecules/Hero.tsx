interface HeroProps {
  fullScreen?: boolean;
  children: React.ReactNode;
}

const Hero = ({ children, fullScreen }: HeroProps) => {
  return (
    <>
      <div className={"hero bg-base-200 " + (fullScreen ? "min-h-screen" : '')}>
        <div className="hero-content text-center">
          {children}
        </div>
      </div>
    </>
  );
};

export default Hero;
