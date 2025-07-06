const fromUnknow = (error: unknown) => {
  console.error(error);
  return {
    type: 'UNKNOWN',
    title: 'Unknown error',
    status: 500,
    detail: 'An unknown error occurred',
    violations: [],
  };
};

export default fromUnknow;
