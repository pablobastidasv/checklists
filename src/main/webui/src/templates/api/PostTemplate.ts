import axios from "axios";
import fromError from "../../utils/ProblemDetails/handleError";
import { logger } from "../../logging";

export interface PostTemplateRequest {
  id: string;
  name: string;
  description: string;
}

const postTemplate = async (template: PostTemplateRequest) => {
  try {
    await axios.post('/api/templates', template);
  } catch (error) {
    const problemDetailError = fromError(error);
    logger.error('Failed to post template', problemDetailError);
  }
};

export default postTemplate;
