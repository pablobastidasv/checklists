import type { Template } from "../models/Template";

const getTemplates = async (): Promise<Template[]> => {
  return [
    {
      id: '4847462e-e80d-4999-b1bd-37593c353c34',
      name: 'Daily Routine',
      description: 'Start your day right with this comprenhensive morning routine',
      itemsCount: 6,
    },
    {
      id: '2b1f7b0e-6da5-4c88-9c0a-6ec8df8b686f',
      name: 'Project Launch',
      description: 'Complete checklist for launching a new project',
      itemsCount: 15,
    },
    {
      id: 'e1f5c0e0-d8d2-4f0a-b2d4-a9f8f3c7b8c6',
      name: 'Travel Packing',
      description: 'Checklist for packing your travel bags',
      itemsCount: 10,
    },
  ];
};

export default getTemplates;
