FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://hello_world.patch \
	           file://zephyr_as_dom0.patch \
	       	   file://zephyr.conf"
