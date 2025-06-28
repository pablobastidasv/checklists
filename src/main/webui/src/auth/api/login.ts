interface ApiResponse<T> {
  status: number;
  data: T;
  controller: AbortController;
}

interface LoginResponse {
  token: string;
  email: string;
  roles: string[];
}

const login = async (username: string, password: string): Promise<ApiResponse<LoginResponse>> => {
  const controller = new AbortController();
  const { signal } = controller;

  const response = await fetch('/api/auth/login', {
    signal,
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ email: username, password }),
  });

  const status = response.status
  const data = await response.json();
  return {
    status,
    data,
    controller
  };
}

export default login;


