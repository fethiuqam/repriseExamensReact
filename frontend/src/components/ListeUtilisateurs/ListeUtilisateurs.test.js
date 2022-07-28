import { render, screen } from "@testing-library/react";
import { mockServer } from "../../mocks/server";
import { mockUtilisateurs } from "../../mocks/mockData";
import ListeUtilisateurs from "./ListeUtilisateurs";
import AuthContext from "../../context/AuthProvider";
import { BrowserRouter } from "react-router-dom";

beforeAll(() => mockServer.listen({ onUnhandledRequest: "error" }));

afterEach(() => mockServer.resetHandlers());

afterAll(() => mockServer.close());

test("devrait afficher tous les utilisateurs", async () => {
  render(
    <AuthContext.Provider
      value={{ type: "personnel", permissions: ["GererUsagers"], id: null }}
    >
      <BrowserRouter>
        <ListeUtilisateurs />
      </BrowserRouter>
    </AuthContext.Provider>
  );
  mockUtilisateurs._embedded.utilisateurs
    .map(({ codeMs }) => codeMs)
    .forEach(async (codeMs) =>
      expect(await screen.findByText(codeMs)).toBeInTheDocument()
    );
});
