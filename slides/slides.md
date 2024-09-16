---
defaults:
  layout: center
---

<link rel="stylesheet" href="/style.css"/>

# Full-stack cljs web apps
## with and without Sitefox

By Chris McCormick

chris@mccormick.cx

<!--

PRE: big terminal font, `npm run dev`, presenter mode

Hey, thanks for coming to the workshop today.
My name is Chris and today we are going to look at full-stack
ClojureScript web apps with and without Sitefox.

What full-stack ClojureScript means is that you have ClojureScript
running not just in the browser, but also on the server, on the backend.

Sitefox is the framework I built to give myself a headstart building
ClojureScript back-ends. Hopefully some of you will find it useful,
and at the very least you'll learn about what kinds of things a web
backend needs to do.
-->

---

# ClojureScript w/o the JVM

## why?

<v-clicks>

- Deployment
- Dependencies
- Experience
- Startup
- Unified

</v-clicks>

<footer>
  <code>git clone https://github.com/chr15m/hoc-2024</code>
  <code>./check-node-version</code>
</footer>

<!--
Before we start I'm going to get you to check out the repo for this workshop.
You can see the git clone command in the bottom left.

Let's all go ahead and run that to get the code checked out.

We do have a lot of ground to cover today.
If at any point we are moving too fast, please feel free to raise your hand and tell me to slow down.

The repo contains these slides and it also contains
an example folder that we will get to soon.

To make sure everybody has the minumum version of Node installed, please run the `./check-node-version` script.

If you don't have a recent enough Node version it will tell you how to install it with nvm. If you want to use your package manager or a different node version manager like `fnm` you can do that too.




Today we're looking at running ClojureScript without the JVM.

Why would we even want to do this? The JVM is mature and efficient and Clojure was carefully designed to run on top of it. Why would we want to run ClojureScript code on the server backend as well as the browser frontend?

Here are some of the reasons you might want to do this.

Your deployment environment might be optimized for JavaScript. For example we might be deploying to a Platform-as-a-Service like Netlify. Or we might have a legacy internal company architecture that is full optimized for deploying JavaScript on the server.

We might have a dependency on a library that is only available as a node package. Maybe you're working in some niche area and there is no Java library for doing the thing you want, but there is a JavaScript library on npm.

You or your team might have a lot of knowledge and experience built up around the Node ecosystem. You might have more experience with Node and JavaScript than Java and the JVM.

You might be after those fast startup times. The Node VM starts fast.

You might be looking for a more unified frontend and backend experience. A closer match between your front end and backend code and especially the interop environment. For example you might want to import and use the same 3rd party node libraries in the browser and on the server side.

So those are some reasons why we might want to run ClojureScript on the backend as well as the frontend.

-->

---

# Ways to run CLJS (without the JVM)

### Backend


|             | Compiled    | Java       | Clj data        | Reagent?       | ![](/bdude.png) |
|-------------|-------------|------------|-----------------|----------------|-----------------|
| shadow-cljs | compiled    | java build | clj collections | as library     |                 |
| nbb         | interpreted | no build   | clj collections | built in       | ✔               |
| squint      | compiled    | node build | js native       | native react   | ✔               |

### Frontend

|             | Compiled    | Java       | Clj data        | Reagent?       | ![](/bdude.png) |
|-------------|-------------|------------|-----------------|----------------|-----------------|
| shadow-cljs | compiled    | java build | clj collections | as library     |                 |
| scittle*    | interpreted | no build   | clj collections | js module      | ✔               |
| squint      | compiled    | node build | js native       | native react   | ✔               |

* sitefox & cherry

<!--

Today we're going to take a whirlwind tour of these different technologies for running ClojureScript. I'm going to show you minimal examples of getting each one running.

I've divided these into backend and frontend. You can mix and match these different technologies picking one from the backend list and one from the frontend list. On the backend we have shadow-cljs, nbb, and squint. On the frontend we have again shadow-cljs, squint, and scittle.

We're going to look specifically at the reaons and tradeoffs to help you decide when to pick each one. The tradeoffs are summarized here in the columns.

The "compiled" column tells us whether a particular tool has a compilation step, or if you can deploy and run it directly.

The "Java" column tells us if the tool needs you to have Java installed at compile time. You can see here that shadow-cljs needs Java at compiled time.

The "Clj data" column tells us whether the tool uses Clojure datastructures under the hood, with the immutability guarantees that come with that.

Finally, the "Reagent" column tells us whether we can use the Reagent library. This is important because there's a lot of Reagent knowledge, experience, and probably legacy code lying around. I've indicated Reagent on the backend as well because I think that's a nice advantage - using the same components on the server and client.

Note that where Reagent is not available it means no Clojure libraries are available. So loading any Clojure libraries is out.

Just a couple more notes to finish off.

- First, scittle has an asterisk because Michiel Borkent told me it's technically also possible to run scittle on the backend via webworkers.
- Second, Sitefox is not on here because it's not a tool for running ClojureScript. It's a library that integrates with some of these tools. Sitefox uses Reagent internally quite a bit so you can only use it with the tools that have Reagent integration.
- Finally there is another tool called cherry, which is related to squint, but at this point I think it's not as widely used. Cherry is like squint but with semantics closer to traditional ClojureScript. I don't know too much about cherry, but it looks like it could one day be a replacement for shadow-cljs in some situations. Unlike squint it uses native Clojure datastructures and compiles to es6 and integrates well with the existing JavaScript ecosystem. I don't think you can use Reagent with it easily.

-->

---

# The examples

- nbb
- shadow frontend
- shadow fullstack
- scittle
- squint
- sitefox (on shadow-cljs)

<!--

These are the six examples we are going to go through.

We have nbb, shadow-cljs frontend, shadow-cljs "full stack", scittle, squint, and finally Sitefox running on shadow-cljs. There's a lot here so let's get started.

-->

---

# nbb

### Cljs interpreter for Ad-Hoc Node scripting

`examples/nbb`

<br>

<v-clicks>

## tradeoffs

- easy to get started
- no compile step
- fast startup time
- backend only
- consume Node deps
- consume Clojure deps
- small artifact (1.2Mb JS)
- macros
- reagent available
- no live-reloading built in

</v-clicks>

<!--

Let's start with the nbb example.

Nbb provides an easy way to run ClojureScript on the Node runtime.

With all of these examples we are going to change into the folder, take a look at the files, do an npm install, and then run the examples.


- Look at package.json - note it is minimal
- Run `npx nbb hello.cljs` - startup time is fast.
- `npx nbb server.cljs` - uses Node express library to run a webserver. Visit the page.
- `npx nbb server_reagent.cljs` - Note the button does nothing becuase it's server side rendered.



Let's talk about the tradeoffs when using nbb.

It's easy to get started with a simple package.json and standard Node tooling.

There's no compile step, you can just run your code directly on Node.

As we've observed it has a fast startup time.

Nbb only runs on the backend on Node, you can't run it in the browser.

You can easily consume Node dependencies as we saw with the `express` web server in the server demo. You just add the deps to your package.json, do `npm install`, and then you can require them.

It's also possible to consume Clojure deps. To do this you need to have babashka installed and you specify the deps in `nbb.edn`. We won't got into this but it's good to bear in mind.

Nbb is distributed as a compiled JavaScript artifact. When you `npm install` it you only pull down 1.2Mb which is pretty good for a node dependency. Of course this is on the server side where artifact size is maybe less important.

Macros are available.

Reagent is built in and you don't have to install it as a dep.

Unlike shadow-cljs there is no built in live-reloading and much of the other extended shadow-cljs tooling is missing. This makes it more suitable for simpler backends.

-->

---

# shadow-cljs (frontend)
### traditional ClojureScript build tool

`examples/shadow-frontend`

<!--

- Switch to `examples/shadow-frontend`.
- Frontend is where shadow-cljs has traditionally been used lately.
- `ls` and `npm install` and look at the files.
- Note package.json, shadow-cljs.edn, public/index.html because frontend, and src tucked away.
- Node deps defined in package.json.
- Clojure deps defined in shadow-cljs.edn.
- `npx shadow-cljs watch app`
- Visit localhost:8000
- Changes are hot-loaded into the browser.
- Note localhost:9630 tooling.
- Killer feature of real time hot loading.
- Separate command to compile to production artifact `npx shadow-cljs release app`
- Note the size `275k` which includes compiled cljs, React, and compiled Reagent.

-->

---

# shadow-cljs (full-stack)
### compiled output run on Node

`examples/shadow-fullstack`

<br>

<v-clicks>

### tradeoffs

- more complex setup
- compilation step
- dependent on Java
- slower startup time
- consume Node deps
- consume Clojure deps
- medium sized artifact (275kb JS)
- macros
- reagent available
- great tooling

</v-clicks>

<!--

Next up is the shadow-cljs full-stack example.

- compiling two JavaScript artifacts
- one in the browser like before
- an additional target run on Node on the server
- npm install and look at the files
- look at extra files
- look at shadow-cljs.edn change
- look at server.cljs - similar to nbb server

```
npx shadow-cljs watch app server
node devserver.js
```

- note the common component in `common.cljs`

- additional complexity from two different processes

- look at tradeoffs

- possible to get artifact size down but it's tricky

-->

---

# squint
### a new borkdude cljs variant

`examples/squint`

<br>

<v-clicks>

### tradeoffs

- setup varies from simple to complex
- native node integrated builds
- dependent on node not java at build
- can consume node deps
- no clojure deps
- reagent not available
- uses javascript datastructures
- smaller artifact sizes
- macros
- tooling from javascript

</v-clicks>

<!--

Now we're going to look at Squint.

- dialect of ClojureScript with compiler and standard library
- "a tool to target JS when you need something more light-weight in terms of interop and bundle size."
- only uses built-in javascript datastructures, not Clojure collections
- Squint's output is designed to work well with ES modules.

- look at files
- package.json
- `npx squint run example.cljs`
- look at example.mjs

- `npx squint run server.cljs`
- note the #html tag
- note about `node --watch`

- go into frontend
- uses a javascript build tool called vite
- squint.edn - for configuring watch builds
- `npx squint watch`
- `npx vite --config viteconfig.js public`

- note tighter integration with react

- has #html and #jsx tags
- leverages the native javascript tooling

- tradeoffs

-->

---

# scittle
### frontends without build

`examples/scittle`

<br>

<v-clicks>

### tradeoffs

- super simple setup
- no build at all
- not dependent on java or node
- consume JS modules from CDN
- some clojure deps pre-compiled
- larger artifact (1.14MB)
- macros
- reagent available
- basic tooling

</v-clicks>

<!--

This scittle demo is quite different to the examples we've looked at so far.

- look at files
- everything in index.html
- no npm install
- just open it in the browser
- full reagent app, look at source
- we're loading the Scittle ClojureScript interpreter from CDN.
- also loading pre-compiled deps from CDN.
- network tab = 1.14 MB (203.86 kB transferred)

-->

---

# Sitefox

![](/sitefox.svg)

<!--

Introduction to Sitefox, what is is and why it exists.

- story of why i built it:
  - coming from django
  - trying to bootstrap sites fast

- opinionated
- batteries included
- 12 factor
- mostly glue code

-->

---

# Sitefox

`npm init sitefox-shadow-fullstack@latest sitefoxdemo`

<v-clicks>

## Batteries included

- Routing (Express)
- Templates (Reagent)
- Key-value store + Database (Keyv)
- Sessions (Express)
- Authentication (passport.js)
- Email (Nodemailer)
- Forms (node-input-validator)
- Logging (rotating-file-stream)
- Live reloading (shadow-cljs)

</v-clicks>

<!--

- go into `examples/sitefox`
- look at README
- explain about init scripts
- sets up a shadow-cljs full stack project with sitefox
- follow the instructions

- demos:
  - routing
  - templates (select-apply?)
  - kv & database
  - sessions
  - email

-->

---

# Full-stack cljs web apps
## with and without Sitefox

By Chris McCormick

chris@mccormick.cx

<!--

- Thanks
- Special thanks to Dustin Getz
- How to contact me

-->

