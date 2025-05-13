const getChecklists = async () => {
  const response = await fetch('/api/checklists');
  const data = await response.json();
  return data;
};

export { getChecklists };
