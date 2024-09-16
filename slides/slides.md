---
defaults:
  layout: center
---

<link rel="stylesheet" href="/style.css"/>

<!--

Traditionally ClojureScript runs on the front-end in the browser.
Probably the most common way to run ClojureScript in the browser today is to use shadow-cljs.


# TODO:
# - [x] put git repo address in talk
# - [x] get the install flow checked with versions
# - [x] Think about nvm isolation and out of date shadowfront for example
# - [ ] Create .node_version file?
# - [ ] Replace shadowfront step with an example folder in the GH repo
# - [ ] Examples of everything
# - [ ] Artifact size comparisons?


# - 6 slides with bullets of pros/cons

Through examples show what these things are, get them running so you can understand the tradeoffs.
Subtext: full stack app with cljs without JVM.

My motivation: full stack js withou the jvm. sitefox is a framework for this. before we get to sitefox we need to understand the different ways to run clojurescript without the JVM and their tradeoffs.

-->

# Full-stack cljs web apps
## with and without Sitefox

By Chris McCormick

chris@mccormick.cx

<!--

Hey, thanks for coming to the workshop today.
My name is Chris and today we are going to look at full-stack
ClojureScript web apps with and without Sitefox.

What full-stack ClojureScript means is that you have ClojureScript
running not just in the browser, but also on the server, on the backend.

Sitefox is the framework I built to give myself a headstart building
ClojureScript back-ends. Hopefully some of you will find it useful,
but at the very least you'll learn about what kinds of things a web
backend needs to do.
-->

---

# Before we start
### Check out the repo

<img src="/repo-qr.png" width=200/>

`git clone https://github.com/chr15m/hoc-2024`

<!--
Before we start I'm going to get you to check out the repo for this workshop.
The repo contains these slides and it also contains
an example folder that we will get to soon.

I will leave this QR code and git repo up on the next couple of slides.
-->

---

# Workshop structure

- 30 minutes intro and deps
- 30 minutes = 5 examples
- 30 minutes about Sitefox

<br><br>

<img src="/repo-qr.png" width=150/>

`git clone https://github.com/chr15m/hoc-2024`

<!--
This is the structure of today's workshop.

We're going to spend 30 minutes on this current part,
which is the intro and getting dependencies installed.

Then we are going to spend 30 minutes in a whirlwind tour
of different ways to run ClojureScript without using the JVM.

Then we are going to spend 30 minutes looking at Sitefox.
-->

---

# Before we start
### Checking node version

`./check-node-version`

<br>
<br>

<img src="/repo-qr.png" width=150/>

`git clone https://github.com/chr15m/hoc-2024`


<!--
Once we have the git repo checked out,
we're going to run this script to check your node version.

If you don't have a recent enough node installed, or any node installed,
it's going to suggest you install nvm or "Node Version Manager".
This is an easy way to install specific versions of node.
If you want to use homebrew or your OS packages that's fine too,
as long as your version of node is recent enough to run the examples.
-->

---

# ClojureScript w/o the JVM

<v-clicks>

- Beyond shadow-cljs
- ClojureScript on the server
- Sitefox framework

</v-clicks>

<footer>
  <code>https://github.com/chr15m/hoc-2024</code>
  <code>./check-node-version</code>
</footer>

<!--
I'm going to crack on with these intro slides but please note the
GitHub repo in the bottom left and the command you need to run in
the bottom right. I'll leave those there and we'll check in with
everyone shortly.

So I want to show you three main things today.

Broadly speaking these three things are about running ClojureScript without depending on the JVM at runtime.

The first thing is about building and running ClojureScript in new ways beyond just shadow-cljs, and the tradeoffs of doing that, including stacks that don't need the JVM at compile time.

The second thing is about running ClojureScript on the back-end, on the server side, or in the cloud, not just in the browser.

The third thing is about my own backend web development framework Sitefox, and how that fits into this puzzle of building ClojureScript web technology.
-->

---

# Going all-in on ClojureScript

### what it means

<footer>
  <code>https://github.com/chr15m/hoc-2024</code>
  <code>./check-node-version</code>
</footer>

<!--
Let's take a closer look at what going full-stack, all-in on ClojureScript means.
-->

---

# Going all-in on ClojureScript

```mermaid
graph TD
B[ClojureScript<br>browser] --> C[Clojure<br>JVM]
```

<footer>
  <code>https://github.com/chr15m/hoc-2024</code>
  <code>./check-node-version</code>
</footer>

<!--
This is a simplified view of the traditional way to run ClojureScript and Clojure together.

Here you can see ClojureScript, usually compiled to JavaScript with shadow-cljs these days, or possibly lein and figwheel, and it is running in the browser.

Below that you can see Clojure running on the server, often from a jar file and using the JVM.
-->

---

# Going all-in on ClojureScript

```mermaid
graph TD
B[ClojureScript<br>browser] --> C[ClojureScript<br>node.js]
```

<footer>
  <code>https://github.com/chr15m/hoc-2024</code>
  <code>./check-node-version</code>
</footer>

<!--
What I'm talking about today is this setup.

The frontend is the same, and we've replaced Clojure on the JVM with ClojureScript running on the Node JavaScript VM instead.

What this means in practice is our interop is no longer calling into Java functions and libraries, but now we are calling into JavaScript functions and libraries and leveraging the Node modules ecosystem on the server side.
-->

---

# Full-stack ClojureScript

## why?

<v-clicks>

- Deployment
- Dependencies
- Experience
- Startup
- Unified

</v-clicks>

<footer>
  <code>https://github.com/chr15m/hoc-2024</code>
  <code>./check-node-version</code>
</footer>

<!--
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

|             | Compiled | Java  | Clj data | Reagent compat | ![](/bdude.png) |
|-------------|----------|-------|----------|---------|---|
| shadow-cljs | compiled | java build | clj collections    | as library       |   |
| nbb         | interpreted    | no build | clj collections       | built in       |  ✔ |
| squint      | compiled | node build  |  js native        | native react |  ✔ |

### Frontend

|             | Compiled | Java | Clj data | Reagent | ![](/bdude.png) |
|-------------|----------|------|----------|---------|---|
| shadow-cljs | X        | X    | X        | X       |   |
| scittle*    |          |      | X        | X       | X |
| squint      | X        |      |          |         | X |

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

# The Goal
## zero to "hello world"
## (tools .forEach)

<!--
What we are going to do today is go from zero to a "hello world" web app using each of the technologies or tools I just showed you.

For each tool we're going to get it running, look at the source code, and also take a look at the tradeoffs.
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


