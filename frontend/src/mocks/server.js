import {rest} from 'msw'
import {setupServer} from 'msw/node'
import {personnelItems} from "./mockData";

const handlers = [
    rest.get('/api/demandes', (req, res, ctx) => {
            return res(
                ctx.status(200),
                ctx.json(personnelItems)
            )
        }
    )
];

export const mockServer = setupServer(...handlers);
