# Banka Frontend

## Usage

### NPM
#### Prerequisites

- [NodeJS](https://nodejs.org/en/)

```shell
cd banka-front
npm install
npm start # starts UI
npm run storybook # starts Storybook
```


### Via Docker (OSX|Linux only)
For Windows hot reload isn't working. See this [issue](https://github.com/microsoft/WSL/issues/4739) for more information.

#### Prerequisites
- [Docker](https://www.docker.com/)

```shell
cd banka-front
docker compose up --build
```

Once the app is up and running it can be accessed at:

- UI `localhost:3000`
- Storybook `localhost:6006`

## Technologies

- [NodeJS](https://nodejs.org/en/)
- [React](https://nodejs.org/en/)
- [Storybook](https://storybook.js.org/)
- [TailwindCSS](https://tailwindcss.com/)
