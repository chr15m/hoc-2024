---
defaults:
  layout: center
---

<link rel="stylesheet" href="/style.css"/>

# The examples

---

# nbb
### great for backends

<v-clicks>

```shell
cd examples/nbb
ls
npm install
```

```
package.json
hello.cljs
server.cljs
server_reagent.cljs
```

package.json
```json
{
  "dependencies": {
    "express": "^4.21.0",
    "nbb": "^1.2.192",
    "react": "^17.0.2",
    "react-dom": "^17.0.2"
  }
}
```

</v-clicks>

<!--

Nbb provides an easy way to run ClojureScript on the Node runtime.

The first thing to do, as with all of the following examples, is change into the folder and run `ls` and then `npm install` which will install the dependencies.

There are four files here. The first is a standard Node manifest file `package.json` which describes the dependencies which in this case are nbb itself and express and React which I'll get to.

The goal of nbb is to make it easy to do ad-hoc scripting with ClojureScript on Node.js and the first thing to notice here is that it lives up to that promise. You literally only need a package.json file specifying nbb should be installed and you're ready to go.

The other three files are ClojureScript source examples.

-->

---

# nbb
### great for backends

<v-clicks>

```
npx nbb hello.cljs
```

```
npx nbb server.cljs
```

```
npx nbb server_reagent.cljs
```

</v-clicks>

<!--

The second interesting thing to note is that there is no compile step and the startup time is fast. Go ahead and run `npx nbb hello.cljs` and you'll see it print "hello world" immediately. You can look at the source for that file and see it's just a print statement.

Now take a look at server.cljs - what this does is use Node's express server to stand up a simple webserver. After running it with `npx nbb server.cljs` it runs a simple webserver on port 8000. Visit that page in your web browser to see the hello world string being served.

The final example you can run with `npx nbb server_reagent.cljs`. The main thing to note here is server side Reagent rendering. You need to have React installed to do this because Reagent relies on React. You don't need to install Reagent itself because nbb comes with Reagent pre-compiled.

Notice that I have put a button in the Reagent example, but it doesn't do anything when clicked. This is because we are rendering server side so everything gets turned into plain HTML and of course no event handlers can be found.

-->

---

# nbb
### tradeoffs

<v-clicks>

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

# Ways to run CLJS (without the JVM)

### Backend

<div class="hi-2">

|             | Compiled | Java | Clj data | Reagent | ![](/bdude.png) |
|-------------|----------|------|----------|---------|---|
| shadow-cljs | X        | X    | X        | X       |   |
| nbb         |          |      | X        | X       | X |
| squint      | X        |      |          |         | X |

</div>

### Frontend

|             | Compiled | Java | Clj data | Reagent | ![](/bdude.png) |
|-------------|----------|------|----------|---------|---|
| shadow-cljs | X        | X    | X        | X       |   |
| scittle*    |          |      | X        | X       | X |
| squint      | X        |      |          |         | X |

<!--

I've highlighted nbb in the table here. So we can see the main properties being it is server side running on Node, it is not compiled, it does not need Java to compile as there is no compile step, it uses native Clojure datastructures, it has Reagent built in, and it's a borkdude project.

-->

---

# shadow-cljs
### pure front-end

<v-clicks>

```shell
cd examples/shadow-frontend
ls
npm install
```

```
shadow-cljs.edn
package.json
src/app/core.cljs
public/index.html
```

</v-clicks>

<!--

Now let's switch to the frontend. This is where shadow-cljs has traditionally been used.

We get started in the same way by going into the folder and doing ls and `npm install`.

Looking at the file structure, our setup is a little more complex than nbb. We have the package.json and the source file, which is in the src/app folder.

The package.json this time contains shadow-cljs itself, and React.
We also have a shadow-cljs.edn file. This tells Shadow-cljs where the source is, defines Clojure dependencies, which here is Reagent, and then defines the app build since we are dealing with a compilation phase here.

Finally the index.html is there because this is a frontend app and the browser needs to load something.

-->

---

# shadow-cljs
### pure front-end

<v-clicks>

```
npx shadow-cljs watch app
```

```
npx shadow-cljs release app
```

</v-clicks>

<!--

We run this with `npx shadow-cljs watch app` which sets up the compile and watch loop. Shadow-cljs provides its own dev server and we can visit localhost 8000 to see the page. This time we can click the buttons and the interaction happens as the Reagent rendering is happening in the browser not on the server side.

An interesting thing here is we get all this nice built-in development mode tooling. For example on localhost 9630 we get this dashboard that shows the builds and runtimes and connected browsers. It also has all these nice features like taps and a REPL.

Of course we also get the killer feature of live real-time hot loading of our code changes. When we edit and save the source file we immediately see the changes in the browser.

To compile the app into a production artifact there is this separate build command `npx shadow-cljs release app`. I've set it up here to compile into the build folder.

If we take a look in the build folder we find a main.js artifact that is `275k`. That's a non-trivial amount of JavaScript sent to the frontend. Of course these days there are far heavier JavaScript bundles out there but it is still a non-trivial amount of code for what is a fairly simple interaction.

-->

---

# shadow-cljs
### full stack

<v-clicks>

```shell
cd examples/shadow-fullstack
ls
npm install
```

```
shadow-cljs.edn
package.json
src/app/core.cljs
src/app/server.cljs
public/index.html
```

</v-clicks>

<!--

Now we're going to look at running ClojureScript in both the browser and on the server with shadow-cljs. We do this by compiling two different artifacts, one for the client and one for the server. We run the server JavaScript using Node.

Again let's change into the folder, list the files, and npm install the deps.

This time we have a similar setup to the frontend example, but we have an additional file in src/app/server.cljs which of course holds our server code.

To compile this we have a new section in the `shadow-cljs.edn` which specifies how to build the server. Note we've got a different target of `:node-server` and we write it out to devserver.js during watch mode.

Our server.cljs looks similar to the nbb server we looked at before with a couple of differences we'll look at once we get it running.

-->

---

# shadow-cljs
### full stack

```
npx shadow-cljs watch app server
node devserver.js
```

<!--

Let's get this stack running. You can see here we have some additional complexity. Like before we ask shadow-cljs to do the compile and watch loop. This time however we also need to run the server side artifact itself and we do that by using node.

There are different ways to invoke these different processes at the same time and kill them when the dev server is stopped but this does add a bit of complexity since we now have two compiled artifacts.

- test page static rendered
- common component
- no live-reloading of routes
- what if you want to template things?

-->

---

# shadow-cljs
### tradeoffs

<v-clicks>

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

Let's take a look at the tradeoffs.

The set up is a bit more complex with more files.

There is a compilation step before we can get our code running. This is true both in development and when making a production build. The code has to be compiled before we can run it.

The compile step is depndent on having Java installed.

The startup time for the dev server to get the app compiled and served is on the order of 10 seconds.

We didn't demonstrate this but we can consume Node dependencies. You can add them to package.json and require them just like with nbb on the server side.

We can also consume Clojure deps by putting them into the shadow-cljs.edn dependencies section.

Our artifact size for a Reagent app is medium sized. It's possible to compile very small frontends with shadow-cljs but you have to forgo Reagent and React. You can get even smaller, down to under 10kb, by getting rid of any native Clojure datastructures but you really have to start contorting things to get down there.

We have macros, which are run at compile time via Clojure.

We have Reagent available via the Clojure dependency pathway.

Finally, we have a really good tooling and live-reloading experience.

-->

---

# Ways to run CLJS (without the JVM)

<div class="hi-1">

### Backend

|             | Compiled | Java | Clj data | Reagent | ![](/bdude.png) |
|-------------|----------|------|----------|---------|---|
| shadow-cljs | X        | X    | X        | X       |   |
| nbb         |          |      | X        | X       | X |
| squint      | X        |      |          |         | X |

### Frontend

|             | Compiled | Java | Clj data | Reagent | ![](/bdude.png) |
|-------------|----------|------|----------|---------|---|
| shadow-cljs | X        | X    | X        | X       |   |
| scittle*    |          |      | X        | X       | X |
| squint      | X        |      |          |         | X |

</div>

<!--

So shadow-cljs ClojureScript can target both the backend and frontend, it has a build step, it relies on Java at build time, it uses native ClojureScript datastructures, and you can use Reagent with it.

-->


---

# squint
### a new borkdude cljs variant

<!--

Now we're going to take a look at Squint.

Note that there is a more detailed and in depth talk about Squint later in the day by Felix Alm called "a taste of Clojure for JavaScript devs".
-->


---

# Ways to run CLJS (without the JVM)

<div class="hi-3">

### Backend

|             | Compiled | Java | Clj data | Reagent | ![](/bdude.png) |
|-------------|----------|------|----------|---------|---|
| shadow-cljs | X        | X    | X        | X       |   |
| nbb         |          |      | X        | X       | X |
| squint      | X        |      |          |         | X |

### Frontend

|             | Compiled | Java | Clj data | Reagent | ![](/bdude.png) |
|-------------|----------|------|----------|---------|---|
| shadow-cljs | X        | X    | X        | X       |   |
| scittle*    |          |      | X        | X       | X |
| squint      | X        |      |          |         | X |

</div>

---

# scittle
### no-build-step frontends

<!--
When and why to use scittle.
-->

---

# Ways to run CLJS (without the JVM)

### Backend

|             | Compiled | Java | Clj data | Reagent | ![](/bdude.png) |
|-------------|----------|------|----------|---------|---|
| shadow-cljs | X        | X    | X        | X       |   |
| nbb         |          |      | X        | X       | X |
| squint      | X        |      |          |         | X |

### Frontend

<div class="hi-2">

|             | Compiled | Java | Clj data | Reagent | ![](/bdude.png) |
|-------------|----------|------|----------|---------|---|
| shadow-cljs | X        | X    | X        | X       |   |
| scittle*    |          |      | X        | X       | X |
| squint      | X        |      |          |         | X |

</div>

