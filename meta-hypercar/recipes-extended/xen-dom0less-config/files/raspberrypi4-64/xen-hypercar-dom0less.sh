#!/bin/sh
# 0 - Dom0 
# 1 - zephyr
# 2 - Linux
# 3 - Linux
/usr/lib/xen/bin/init-dom0less
xl block-attach 2 /dev/mmcblk0p3,,xvda1
# Sometimes xen block drivers race and cause kernel panic
# adding delay between attaches to work this around
sleep 1
xl block-attach 3 /dev/mmcblk0p4,,xvda1
