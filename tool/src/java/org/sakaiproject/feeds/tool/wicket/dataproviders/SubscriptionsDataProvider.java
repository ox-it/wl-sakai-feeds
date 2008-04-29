package org.sakaiproject.feeds.tool.wicket.dataproviders;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sakaiproject.feeds.api.FeedSubscription;
import org.sakaiproject.feeds.tool.facade.SakaiFacade;


public class SubscriptionsDataProvider implements IDataProvider {
	private static final long		serialVersionUID			= 1L;
	public static final int			MODE_SUBSCRIBED				= 0;
	public static final int			MODE_ALL_INSTITUTIONAL		= 1;
	public static final int			MODE_ALL_NON_INSTITUTIONAL	= 2;
	@SpringBean
	private transient SakaiFacade	facade;
	private Set<FeedSubscription>	feeds;
	private int						mode;

	public SubscriptionsDataProvider(int mode) {
		if(facade == null)
			InjectorHolder.getInjector().inject(this);
		this.mode = mode;
	}

	public Set<FeedSubscription> getFeedSubscriptions() {
		if(feeds == null){
			feeds = facade.getFeedsService().getSubscribedFeeds(mode);
		}
		return feeds;
	}

	public void addTemporaryFeedSubscription(FeedSubscription subscription) {
		getFeedSubscriptions().add(subscription);
	}

	public Iterator<FeedSubscription> iterator(int first, int count) {
		return getFeedSubscriptions().iterator();
	}

	public IModel model(Object obj) {
		return new Model((FeedSubscription) obj);
	}

	public int size() {
		return getFeedSubscriptions().size();
	}

	public void detach() {
		//System.out.println("SubscriptionsDataProvider.detach()");
//		if(!modified){
//			System.out.println("SubscriptionsDataProvider.detach()");
//			feeds = null;
//		}
	}
}
