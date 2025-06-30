import axios from "axios";
import { logger } from "../../logging";
import type { Template } from "../models/Template";

interface Response<T> {
  data: T;
  error: string | null;
}

const getTemplates = async (): Promise<Response<Template[]>> => {
  try {
    const response = await axios.get('/api/templates');
    logger.debug('Fetched templates', response.data);
    return {
      data: response.data.items,
      error: null,
    };
  } catch (error) {
    logger.error('Failed to fetch templates', error);
    return {
      data: [],
      error: 'Failed to fetch templates',
    };
  }
};

export default getTemplates;
