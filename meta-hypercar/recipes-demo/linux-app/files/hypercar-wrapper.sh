#!/bin/sh

DOMID=$(basename /dev/disk/by-label/dom-?-root | cut -f 2 -d '-')
/usr/bin/hypercar-app ${DOMID}
