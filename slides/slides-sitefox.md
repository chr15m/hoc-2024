---
defaults:
  layout: center
---

<link rel="stylesheet" href="/style.css"/>

# Sitefox

![](/sitefox.svg)

<!--
Introduction to Sitefox, what is is and why it exists.
- opinionated
- batteries included
- 12 factor
- mostly glue code
-->

```
npm init sitefox-shadow-fullstack sitefox-project
cd sitefox-project
make watch
```

---

# Batteries included

<v-clicks>

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

---

# Sitefox on nbb

<!--
Sitefox runs wherever you have ClojureScript on Node and access to Reagent. That means we can run 

Using Sitefox with nbb (backend only).
-->

---

# Sitefox on shadow-cljs

<!--
Using sitefox with full-stack shadow-cljs.
-->


---

# A basic route

```clojure
(.get app "/"
  (fn [req res]
    (.json res #js {:question 42})))
```

<!--
- Live reloading routes.
-->

---

# Templating

```clojure
(def index-html (fs/readFileSync "index.html"))

(defn component-main []
  [:div
   [:h1 "Hello world!"]
   [:p "This is my content."]])

; this returns a new HTML string that can be returned
; e.g. with (.send res)
(render-into index-html "main" [component-main])
```

<!--
Fundamental philosophy is to inject Reagent forms into existing HTML.
-->

---

# Database & KV store

```clojure
(let [table (db/kv "sometable")]
  (.set table "key" "42"))

(-> (.get table "key")
  (.then (fn [val] (print val))))

(let [c (db/client)]
  (-> (.query c "select * from sometable WHERE x = 1")
    (.then (fn [rows] (print rows)))))
```

<!--
Basic philosophy is to start with key values and solidify with relational later.

Configured with environment variables.
-->

---

# Sessions & Auth

```clojure
(let [session (aget req "session")]
  (aset session "myvalue" 42))

(aget req "session" "myvalue")

(defn setup-routes [app]
  (let [template (fs/readFileSync "index.html")]
    (web/reset-routes app)
    ; three calls to set up email based authentication
    (auth/setup-auth app)
    (auth/setup-email-based-auth app template "main")
    (auth/setup-reset-password app template "main")
    ; ... add your additional routes here ... ;
    ))
```

<!--
Built in sessions, built in auth routes for doing basic login stuff using Passport.js
-->

---

# Sending email

```clojure
(-> (mail/send-email
      "test-to@example.com"
      "test@example.com"
      "This is my test email."
      :text "Hello, This is my first email from **Sitefox**. Thank you.")
    (.then js/console.log))
```

---

# Sitefox in a nutshell

---

# Full-stack cljs web apps
## with and without Sitefox

By Chris McCormick

chris@mccormick.cx

<!--


-->
