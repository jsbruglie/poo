#!/bin/bash

for f in t*.gif; do cp "$f" "${f//t/T}"; done
