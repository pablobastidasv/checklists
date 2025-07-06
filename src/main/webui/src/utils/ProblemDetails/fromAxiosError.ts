import type { AxiosError } from "axios";
import type { ProblemDetailError } from "./ProblemDetails";

const fromAxiosError = (error: AxiosError) => {
  const { response } = error;
  if (!response) {
    return {
      type: 'UNKNOWN',
      title: 'Unknown error',
      status: 500,
      detail: 'An unknown error occurred',
      violations: [],
    };
  }

  const { data } = response;

  return data as ProblemDetailError;
};

export default fromAxiosError;
