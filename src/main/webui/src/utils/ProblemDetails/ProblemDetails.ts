interface Violation {
  field: string;
  rejectedValue: string | null;
  message: string;
}

interface ProblemDetailError {
  type: string;
  title: string;
  status: number;
  detail: string;
  violations: Violation[];
}

export type { Violation, ProblemDetailError };
