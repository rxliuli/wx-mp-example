#!/bin/bash

# 启动内网穿透
ssh -o ServerAliveInterval=60 -R rx:80:localhost:8080 serveo.net               