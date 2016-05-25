(ns leiningen.new.friboo
  (:require [leiningen.new.templates :refer [renderer year date project-name
                                             ->files sanitize-ns name-to-path
                                             multi-segment]]
            [leiningen.core.main :as main]
            [clojure.string :as str]))

(def render (renderer "friboo"))

(defn db-prefix [name]
  (->> (str/split name #"(-|_)")
       (map first)
       (apply str)))

(defn friboo
  "A Friboo project template"
  [name]
  (let [render (renderer "friboo")
        main-ns (multi-segment (sanitize-ns name))
        data {:raw-name name
              :name (project-name name)
              :namespace main-ns
              :nested-dirs (name-to-path main-ns)
              :db-prefix (db-prefix (project-name name))
              :year (year)
              :date (date)}]
    (main/info data)
    (main/info "Generating a project called" name "based on the 'friboo' template.")
    (->files data
             ["project.clj" (render "project.clj" data)]
             ;["README.md" (render "README.md" data)]
             ;["doc/intro.md" (render "intro.md" data)]
             ;[".gitignore" (render "gitignore" data)]
             ;[".hgignore" (render "hgignore" data)]
             ["resources/db/queries.sql" (render "queries.sql" data)]
             ["resources/migration/V1__initial_schema.sql" (render "schema.sql" data)]
             ["src/{{nested-dirs}}/db.clj" (render "db.clj" data)]
             ["src/{{nested-dirs}}/api.clj" (render "api.clj" data)]
             ["src/{{nested-dirs}}/jobs.clj" (render "jobs.clj" data)]
             ["src/{{nested-dirs}}/core.clj" (render "core.clj" data)]
             ["test/{{nested-dirs}}/core_test.clj" (render "core_test.clj" data)]
             ["test/{{nested-dirs}}/api_test.clj" (render "api_test.clj" data)]
             ;["LICENSE" (render "LICENSE" data)]
             ;["CHANGELOG.md" (render "CHANGELOG.md" data)]
             "resources")))
