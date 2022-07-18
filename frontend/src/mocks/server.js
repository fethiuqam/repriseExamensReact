import { rest } from 'msw'
import { setupServer } from 'msw/node'
import { personnelItems} from "./mockData";

const handlers = [
  rest.get('/api/demandes', (req, res, ctx) => {
    const type = req.url.searchParams.get('type');
    if (type === 'personnel')
      return res(
        ctx.status(200),
        ctx.json(personnelItems)
      )
    else
      return res(
        ctx.status(404)
      ) 
  }
  )
];

export const mockServer = setupServer(...handlers);
