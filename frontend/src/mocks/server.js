import { rest } from 'msw'
import { setupServer } from 'msw/node'
import { commisItems} from "./mockData";

const handlers = [
  rest.get('/api/demandes', (req, res, ctx) => {
    const role = req.url.searchParams.get('role');
    if (role === 'commis')
      return res(
        ctx.status(200),
        ctx.json(commisItems)
      )
    else
      return res(
        ctx.status(404)
      ) 
  }
  )
];

export const mockServer = setupServer(...handlers);