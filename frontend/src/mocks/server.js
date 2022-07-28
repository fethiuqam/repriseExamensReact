import { rest } from "msw";
import { setupServer } from "msw/node";
import { personnelItems, mockUtilisateurs, mockRoles } from "./mockData";

const handlers = [
  rest.get("/api/demandes", (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(personnelItems));
  }),
  rest.get("/api/utilisateurs", (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockUtilisateurs));
  }),
  rest.get("/api/roles", (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockRoles));
  }),
];

export const mockServer = setupServer(...handlers);
