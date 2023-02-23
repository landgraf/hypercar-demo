FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://hello_message.patch \
	           file://zephyr_as_dom0.patch \
	       	   file://zephyr.conf"


inherit deploy
do_deploy:prepend(){
    install -D -m0644 ${WORKDIR}/zephyr.conf ${DEPLOYDIR}/zephyr.conf
}