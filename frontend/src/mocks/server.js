import { rest } from "msw";
import { setupServer } from "msw/node";
import {personnelItems, mockUtilisateurs, mockRoles, mockCoursGroupes, mockCours} from "./mockData";

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
  rest.get("/api/coursGroupes/planification", (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockCoursGroupes));
  }),
  rest.get("/api/cours", (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(mockCours));
  }),
];

export const mockServer = setupServer(...handlers);
