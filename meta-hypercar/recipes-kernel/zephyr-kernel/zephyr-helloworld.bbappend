FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://hello_world.patch \
		   file://mmu_shared.patch \
	       	   file://zephyr.conf"


