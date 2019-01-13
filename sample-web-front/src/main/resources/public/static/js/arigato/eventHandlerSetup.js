const setupHandler = () => {
    $('.arigato.unsetup .fav').on('click', event => {
        var $this = $(event.currentTarget)
        $.ajax({
            url: '/api/v1/arigato/' + $this.data('id') + '/fav',
            type: 'PUT',
            data:{check: ! $this.hasClass('checked')}
        }).done( (data) => {
            $('.favCounts', $this.parent()).text(data.fav_counts)
            $this.toggleClass('checked');
        }).fail( (data) => {
            alert('error'); //TODO Fake It
        })
    });
    $('.arigato.unsetup .delete').on('click', event => {
        var $this = $(event.currentTarget);
        $.ajax({
            url: '/api/v1/arigato/' + $this.data('id'),
            type: 'DELETE'
        }).done( (data) => {
            $this.parents('.arigato').remove()
        }).fail( (data) => {
            alert('error'); //TODO Fake It
        })
    });
    $('.arigato.unsetup .deleteImage').on('click', event => {
        var $this = $(event.currentTarget);
        $.ajax({
            url: '/api/v1/arigato/' + $this.data('arigato-id') + '/image/' + $this.data('id'),
            type: 'DELETE'
        }).done( (data) => {
            $this.parent().remove()
        }).fail( (data) => {
            alert('error'); //TODO Fake It
        })
    });
}
