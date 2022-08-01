import { render, screen } from "@testing-library/react";
import { mockServer } from "../../mocks/server";
import { mockCours } from "../../mocks/mockData";
import ListeCours from "./ListeCours";
import AuthContext from "../../context/AuthProvider";
import { BrowserRouter } from "react-router-dom";

beforeAll(() => mockServer.listen({ onUnhandledRequest: "error" }));

afterEach(() => mockServer.resetHandlers());

afterAll(() => mockServer.close());

test("devrait afficher tous les cours", async () => {
  render(
    <AuthContext.Provider
      value={{ type: "personnel", permissions: ["GererCours"], id: null }}
    >
      <BrowserRouter>
        <ListeCours />
      </BrowserRouter>
    </AuthContext.Provider>
  );
  mockCours._embedded.cours
    .map(({ sigle }) => sigle)
    .forEach(async (sigle) =>
      expect(await screen.findByText(sigle)).toBeInTheDocument()
    );
});
