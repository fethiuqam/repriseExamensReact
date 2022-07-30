const call = async (url, options) => {
  const reponse = await fetch(url, {
    credentials: "include",
    headers: { "Content-Type": "application/json" },
    ...options,
  });
  if (!reponse.ok)
    throw new Error(`Erreur: ${reponse.status} - ${reponse.statusText}`, {
      cause: reponse.status,
    });
  return reponse;
};

export async function get(url) {
  return (await call(url)).json();
}

export async function httpDelete(url) {
  return await call(url, { method: "DELETE" });
}

export async function post(url, payload) {
  return (
    await call(url, { method: "POST", body: JSON.stringify(payload) })
  ).json();
}

export async function patch(url, payload) {
  return (
    await call(url, { method: "PATCH", body: JSON.stringify(payload) })
  ).json();
}
