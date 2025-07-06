import { AxiosError } from "axios";
import fromAxiosError from "./fromAxiosError";
import { logger } from "../../logging";
import type { ProblemDetailError } from "./ProblemDetails";
import fromUnknow from "./fromUnknow";

const fromError = (error: unknown): ProblemDetailError => {
  logger.debug('Error', error);
  if (error instanceof AxiosError) {
    return fromAxiosError(error);
  }

  return fromUnknow(error);
};

export default fromError;
