# full.json

[![Clojars Project](https://img.shields.io/clojars/v/fullcontact/full.json.svg)](https://clojars.org/fullcontact/full.json)
[![Build Status](https://travis-ci.org/fullcontact/full.json.svg?branch=master)](https://travis-ci.org/fullcontact/full.json)

Unified API for reading and writing JSON for Clojure and ClojureScript.

## Reading JSON

`read-json` wraps Cheshire's `parse-string` method, field mapping method defaults to turning field names to `:kebab-cased` keywords.

```clojure
(read-json "{\"foo\": \"bar\", \"baz": 3}")
=> {:foo "bar" :baz 3}

(read-json
  "{\"foo\": \"bar\", \"baz": 3}"
  :json-key-fn clojure.string/uppercase)
=> {"FOO" "bar" "baz" 3}
```

## Writing JSON

`write-json` wraps Cheshire's `generate-string` method, but has implicit
encoders for `DateTime` objects, that will be transformed to strings in ISO
format. It has an optional `json-key-fn` argument that defaults to
`camelCasing` field names.

```clojure
(write-json {:event-time (org.joda.time.DateTime. 2016 1 1 10 11 11) })
=> "{\"eventTime\":\"2016-01-01T17:11:11.000Z\"}"
