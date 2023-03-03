FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " file://debug_fdt.patch "

do_deploy:append(){
	install -d ${DEPLOYDIR}
	if [ -f ${B}/xen/xen ]; then
	   install -m 0644 ${B}/xen/xen ${DEPLOYDIR}/xen
	fi
}

do_configure:prepend(){
	echo "CONFIG_DEBUG=y" >> xen/.config
	echo "CONFIG_EXPERT=y" >> xen/.config
	echo "CONFIG_UNSUPPORTED=y" >> xen/.config
	echo "CONFIG_SCHED_DEFAULT=\"null\"" >> xen/.config
	echo "CONFIG_SCHED_NULL=y"  >> xen/.config
	echo "CONFIG_SCHED_NULL_DEFAULT=y"  >> xen/.config
	echo "CONFIG_IOREQ_SERVER=y"  >> xen/.config
	echo "CONFIG_ARGO=y" >> xen/.config
	echo "CONFIG_STATIC_MEMORY=y" >> xen/.config
	echo "CONFIG_MEM_ACCESS=y" >> xen/.config
	echo "CONFIG_STATIC_SHM=y" >> xen/.config
}