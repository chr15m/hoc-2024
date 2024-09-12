---
defaults:
  layout: center
---

# Full-stack cljs web apps
## with and without Sitefox

By Chris McCormick

chris @ mccormick.cx

<!--

Hey, thanks for coming to the workshop today.
My name is Chris and today we are going to look at full-stack ClojureScript web apps with and without Sitefox.

What full-stack ClojureScript means is that you have ClojureScript running on the backend on the server side, not just in the browser.



Traditionally ClojureScript runs on the front-end in the browser.
Probably the most common way to run ClojureScript in the browser today is to use shadow-cljs.


# TODO:
# - [x] put git repo address in talk
# - [ ] get the install flow checked with versions
# - [ ] Think about nvm isolation and out of date shadowfront for example
# - [ ] Create .node_version file?
# - [ ] Replace shadowfront step with an example folder in the GH repo
# - [ ] Examples of everything
# - [ ] Artifact size comparisons?


# - 6 slides with bullets of pros/cons

Through examples show what these things are, get them running so you can understand the tradeoffs.
Subtext: full stack app with cljs without JVM.

My motivation: full stack js withou the jvm. sitefox is a framework for this. before we get to sitefox we need to understand the different ways to run clojurescript without the JVM and their tradeoffs.

-->

---

# ClojureScript w/o the JVM

<!--
So I want to show you three main things today.
Broadly speaking these three things are about running ClojureScript without depending on the JVM.
The first thing is about building and running ClojureScript in new ways beyond just shadow-cljs, and the tradeoffs of doing that.
The second thing is about running ClojureScript on the back-end, on the server side, or in the cloud, not just in the browser.
The third thing is about my own backend web development framework Sitefox, and how that fits into this puzzle of building ClojureScript web technology..
-->

---

# Workshop structure

- 30 minutes intro and deps
- 30 minutes = 6 examples
- 30 minutes about Sitefox

<!--
This is the structure of today's workshop.
-->

---

# Before we start
### Check out the repo

<img src="/repo-qr.png"/>

[https://github.com/chr15m/hoc-2024](https://github.com/chr15m/hoc-2024)

<!--
Before we start I'm going to get you to check out the repo for this workshop.
The repo contains the slides
-->

---

# Before we start
### Checking node version

<!--
Before we start let's make sure everybody has the minimum node version required to run the examples.
You can 
-->

---

# Before we start

---

# Going all-in on ClojureScript

- what it means
- why?


<!--
What:
- JVM versus Node/Browser runtime.

Why:
- Deployment environment optimized for JS.
- Library dependency from npm.
- Team has experience with JS ecosystem.
- Fast startup times.

- Non-blocking and event driven architecture (con).
- Unified language for frontend and backend.
- Native handling of web data formats.
- Well suited for websockets, SSE, streaming.

-->

---

# The Goal
## zero to "hello world"

<!--
What we are going to do today is go from nothing to "hello world" using each of the technologies or tools.
Examining the tradeoffs of different ClojureScript tooling.
-->

---

# N ways to run CLJS without the JVM

### CLJS on the Backend

| Tool        | Compiled | Java | Clj data | Reagent |
|-------------|----------|------|----------|---------|
| shadow-cljs | X        | X    | X        | X       |
| nbb         |          |      | X        | X       |
| squint      | X        |      |          |         |

### CLJS on the Frontend

| Tool        | Compiled | Java | Clj data | Reagent |
|-------------|----------|------|----------|---------|
| shadow-cljs | X        | X    | X        | X       |
| squint      | X        |      |          |         |
| scittle*    |          |      | X        | X       |

<!--
Everyone knows shadow-cljs is the default choice.
The disadvantage is the JVM dependency, and it's compiled.
Compiled = more complicated deployment story.
-->


<!--

# Five ways to run CLJS without the JVM

| Tool        | Compiled | Java | Clj data | Frontend | Backend | Reagent |
|-------------|----------|------|----------|----------|---------|---------|
| nbb         |          |      | X        |          | X       | X       |
| shadow-cljs | X        | X    | X        | X        | X       | X       |
| scittle*    |          |      | X        | X        |         | X       |
| squint      | X        |      |          | X        | X       |         |

* sitefox is a framework that runs on top
* cherry is related to squint

-->

<!--

* sitefox is a library/framework you can integrate with these
* scittle can now be used on the back-end but it's a niche use-case for e.g. webworkers.
* cherry's provides is a new lightweight cljs variant that compiles to modern JavaScript and integrates well with the existing ecosystem. So far I haven't found a reason to use it over shadow-cljs for my own projects but it may be worth looking into in the future.

-->


---

# The demos

- nbb
- shadow frontend
- shadow fullstack
- sitefox nbb
- sitefox shadow-cljs

<!--

# The tools

- nbb
- shadow-cljs (frontend)
- shadow-cljs (full stack)
- sitefox (framework / library)
- squint
- scittle

-->

---

# nbb
### great for backends

- interpreted

```
mkdir my-nbb-project
cd my-nbb-project
echo {} > package.json
npm i nbb
npx nbb
```

```clojure
(ns demo)

(print "Hello world!")
```

```
npm i express
```

```clojure
(ns main
  (:require
    ["express$default" :as express]))

(let [server (express)]
  (.get server "/" (fn [_req res] (.send res "Hello world!")))
  (.listen server 3000)
  (print "Server running on port 3000"))
```

```
npx nbb main.cljs
```

---

# shadow-cljs
### pure front-end

`npm init shadowfront myproject`

<!--
I made create-shadowfront as a fast way to bootstrap a shadow-cljs app.
This will install the creat-shadowfront script into your npm cache in your homedir. This will be localized to your node version.
-->

---

# shadow-cljs
### full stack sites

`npm init sitefox-shadow-fullstack myproject`

---

# Sitefox

<!--
Introduction to Sitefox, what is is and why it exists.
- opinionated
- batteries included
-->

---

# Sitefox and nbb

<!--
Using Sitefox with nbb (backend only).
-->

---

# Sitefox and full-stack shadow-cljs

<!--
Using sitefox with full-stack shadow-cljs.
-->

---

# squint
### a new borkdude cljs variant

<!--
When and why to use squint.
-->

---

# scittle
### no-build-step frontends

<!--
When and why to use scittle.
-->

---

# Full-stack cljs web apps
## with and without Sitefox

By Chris McCormick

chris @ mccormick.cx

<!--


-->
